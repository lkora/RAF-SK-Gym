//
//  BanUserRequest.swift
//  GymTeam
//
//  Created by Luka Korica on 1/17/24.
//

import Foundation

struct BanUserRequest: APIRequest {
    
    var endpoint: Endpoint = AdminEndpoint.banUser
    let method = APIRequestMethod.get
    let body: Codable? = nil
    var suffix: String?
 
    init(username: String) {
        suffix = "/\(username)"
    }
}

