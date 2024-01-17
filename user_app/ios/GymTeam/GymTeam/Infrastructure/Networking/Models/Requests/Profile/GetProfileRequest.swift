//
//  GetProfileRequest.swift
//  GymTeam
//
//  Created by Luka Korica on 1/17/24.
//

import Foundation

struct GetProfileRequest: APIRequest {
    
    let endpoint: Endpoint = ProfileEndpoint.getProfile
    let method = APIRequestMethod.get
    let body: Codable? = nil
    var suffix: String? = nil
    
}
