//
//  UsersRequest.swift
//  GymTeam
//
//  Created by Luka Korica on 1/17/24.
//

import Foundation

struct UsersRequest: APIRequest {
    
    let endpoint: Endpoint = AdminEndpoint.users
    let method = APIRequestMethod.get
    let body: Codable? = nil
    var suffix: String? = nil
    
}
