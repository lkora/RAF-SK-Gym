//
//  UnbanUserRequest.swift
//  GymTeam
//
//  Created by Luka Korica on 1/17/24.
//

import Foundation

struct UnbanUserRequest: APIRequest {
    
    var endpoint: Endpoint = AdminEndpoint.unbanUser
    let method = APIRequestMethod.get
    let body: Codable? = nil
    var suffix: String?
 
    init(username: String) {
        suffix = "/\(username)"
    }
}

