//
//  RegisterManagerRequest.swift
//  GymTeam
//
//  Created by Luka Korica on 1/16/24.
//

import Foundation

struct RegisterManagerRequest: APIRequest {
    
    let endpoint: Endpoint = AuthEndpoint.registerManager
    let method = APIRequestMethod.post
    let body: Codable?
    var suffix: String? = nil

    init(params: RegisterManagerParams) {
        let encoder = JSONEncoder()
        body = try? encoder.encode(params)
    }
}


struct RegisterManagerParams: Codable {
    let userName: String
    let password: String
    let email: String
    let birthDate: Date
    let firstName: String
    let lastName: String
    let gymName: String
    let dateOfEmployment: Date

}
