//
//  AuthorizedAPIService.swift
//  GymTeam
//
//  Created by Luka Korica on 1/16/24.
//

import Foundation

protocol ShouldLogoutServiceDelegate {
    func shouldLogout(with error: Error)
}

typealias AuthorizedAPIService = APIService & StoreTokenHandler & RemoveTokenHandler

final class AutorizedAPIServiceDecorator: APIService {
    private let decoratee: AuthorizedAPIService
    
    var delegate: ShouldLogoutServiceDelegate?
    
    init(decoratee: AuthorizedAPIService) {
        self.decoratee = decoratee
    }
    
    // MARK: Functionality
    func perform<T: Decodable>(request: APIRequest, completion: @escaping (Result<T, Error>) -> Void) where T : Decodable {
        decoratee.perform(request: request, completion: handleSession(completion: completion))
    }
    
    func perform(request: URLRequest, completion: @escaping (Result<Data?, Error>) -> Void) {
        decoratee.perform(request: request, completion: completion)
    }
        
    private func handleSession<T: Decodable>(completion: @escaping (Result<T, Error>) -> Void) -> (Result<T, Error>) -> Void where T : Decodable {
        return { result in
            switch result {
            case .failure(let error):
                // Handling different error codes
                if let afError = error.asAFError {
                    if afError.responseCode == 401 {
                        self.delegate?.shouldLogout(with: error)
                    }
                }
                completion(.failure(error))
            case .success(let response):
                completion(.success(response))
            }
        }
    }
}

extension AutorizedAPIServiceDecorator: StoreTokenHandler {
    func save(token: Token) {
        decoratee.save(token: token)
    }
}

extension AutorizedAPIServiceDecorator: RemoveTokenHandler {
    func removeToken() {
        decoratee.removeToken()
    }
}
