//
//  AlamofireAPIService.swift
//  GymTeam
//
//  Created by Luka Korica on 1/13/24.
//

import Foundation
import Alamofire

final class AlamofireAPIService: APIService {
    private let session: Session
    private var token: Token?
    private let baseUrlString: String
    
    private let tokenHandler: TokenHandler = CacheTokenHandler(serviceName: "access-token")
    
    private lazy var decoder: JSONDecoder = {
        let decoder = JSONDecoder()
        decoder.keyDecodingStrategy = .convertFromSnakeCase
        return decoder
    }()
    
    private let tokenFieldKey: String
    
    // MARK: - Initializer
    init(baseUrlString: String, tokenFieldKey: String = "user-access-token") {
        self.baseUrlString = baseUrlString
        self.tokenFieldKey = tokenFieldKey
        self.session = AF
    }
    
    // MARK: - Functionality
    func perform(request: URLRequest, completion: @escaping (Result<Data?, Error>) -> Void) {
        var urlRequest = request
        if let token = getToken() {
            urlRequest.setValue("\(token)", forHTTPHeaderField: tokenFieldKey)
        }
        for header in defaultHTTPHeaders {
            urlRequest.setValue(header.value, forHTTPHeaderField: header.name)
            print(header)
        }
        session.request(urlRequest).response { response in
            switch response.result {
            case .success(let data):
                completion(.success(data))
            case .failure(let error):
                completion(.failure(error))
            }
        }
    }
    
    func perform<T: Decodable>(request: APIRequest, completion: @escaping (Result<T, Error>) -> Void) {
        guard var urlRequest = request.urlRequest(baseURLString: baseUrlString) else {
            completion(.failure(NetworkingError.malformedURL))
            return
        }
        if let token = getToken() {
            urlRequest.setValue("\(token)", forHTTPHeaderField: tokenFieldKey)
        }
        for header in defaultHTTPHeaders {
            urlRequest.setValue(header.value, forHTTPHeaderField: header.name)
            print(header)
        }
        session.request(urlRequest).responseJSON { [weak self] response in
            self?.handle(response: response, completion: completion)
        }
    }
    
    
    private func handle<T: Decodable>(response: AFDataResponse<Any>, completion: @escaping (Result<T, Error>) -> Void) {
        if let responseCode = response.response?.statusCode {
            guard 200..<300 ~= responseCode else {
                if let data = response.data, let object = try? decoder.decode(ErrorResponse.self, from: data) {
                    completion(.failure(object))
                    return
                }
                
                completion(.failure(NetworkingError.parseStatusCode(responseCode)))
                return
            }
        }
        print(response.result)
        switch response.result {
        case .success:
            do {
                _ = try decoder.decode(T.self, from: response.data!)
            } catch let e {
                print(e)
            }
            guard let data = response.data, let object = try? decoder.decode(T.self, from: data) else {
                if let data = response.data, let object = try? decoder.decode(ErrorResponse.self, from: data) {
                    completion(.failure(object))
                    return
                }
                completion(.failure(NetworkingError.missingData))
                return
            }
            completion(.success(object))
        case .failure(let error):
            completion(.failure(error))
        }
    }
}

var defaultHTTPHeaders: [HTTPHeader] {
    defaultHeaderDictionary.map {
        HTTPHeader(name: $0, value: $1)
    }
}

extension AlamofireAPIService: StoreTokenHandler {
    
    func save(token: Token) {
        print("Access Token: \(token)")
        self.token = token
    }
    
    private func getToken() -> String? {
        if let token = token {
            return token
        } else if let token = tokenHandler.getToken() {
            return token
        }
        return nil
    }
}

extension AlamofireAPIService: RemoveTokenHandler {
    
    func removeToken() {
        token = nil
    }
}
