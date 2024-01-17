//
//  EditProfileRequest.swift
//  GymTeam
//
//  Created by Luka Korica on 1/17/24.
//

import Foundation

struct EditProfileRequest: APIRequest {
    let endpoint: Endpoint = ProfileEndpoint.editProfile
    let method = APIRequestMethod.put
    let body: Codable?
    var suffix: String?
    
    init(params: EditProfileParams) {
        let encoder = JSONEncoder()
        body = try? encoder.encode(params)
    }
}


struct EditProfileParams: Codable {
    let userName: String
    let password: String
    let email: String
    let dateOfBirth: Date
    let firstName: String
    let lastName: String
}
