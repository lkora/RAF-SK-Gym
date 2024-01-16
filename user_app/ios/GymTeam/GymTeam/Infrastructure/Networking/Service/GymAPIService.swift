//
//  GymAPIService.swift
//  GymTeam
//
//  Created by Luka Korica on 1/16/24.
//

import Foundation

final class GymAPIService {
    
    private let apiService: APIService
    
    init(apiService: APIService) {
        self.apiService = apiService
    }
    
    
    // Auth
    func login(username: String, password: String, completion: @escaping ((Result<LoginResponse, Error>) -> Void)) {
        apiService.perform(request: LoginRequest(params: LoginParams(userName: username, password: password)), completion: completion)
    }
        
    func registerClient(params: RegisterClientParams, completion: @escaping ((Result<GeneralResponse, Error>) -> Void)) {
        apiService.perform(request: RegisterClientRequest(params: params), completion: completion)
    }
    
    func registerManager(params: RegisterManagerParams, completion: @escaping ((Result<GeneralResponse, Error>) -> Void)) {
        apiService.perform(request: RegisterManagerRequest(params: params), completion: completion)
    }
}
