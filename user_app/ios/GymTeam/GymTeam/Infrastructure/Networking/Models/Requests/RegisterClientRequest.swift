//
//  RegisterClientRequest.swift
//  GymTeam
//
//  Created by Luka Korica on 1/16/24.
//

import Foundation

struct RegisterClientRequest: APIRequest {
    
    let endpoint: Endpoint = AuthEndpoint.registerClient
    let method = APIRequestMethod.post
    let body: Codable?
    
    init(params: RegisterClientParams) {
        let encoder = JSONEncoder()
        body = try? encoder.encode(params)
    }
}


struct RegisterClientParams: Codable {
    let userName: String
    let password: String
    let email: String
    let birthDate: Date
    let firstName: String
    let lastName: String
}
