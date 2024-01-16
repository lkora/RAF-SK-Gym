//
//  LoginRequest.swift
//  GymTeam
//
//  Created by Luka Korica on 1/16/24.
//

import Foundation

struct LoginRequest: APIRequest {
    
    let endpoint: Endpoint = AuthEndpoint.login
    let method = APIRequestMethod.post
    let body: Codable?
    
    init(params: LoginParams) {
        let encoder = JSONEncoder()
        body = try? encoder.encode(params)
    }
}


struct LoginParams: Codable {
    let userName: String
    let password: String
}
