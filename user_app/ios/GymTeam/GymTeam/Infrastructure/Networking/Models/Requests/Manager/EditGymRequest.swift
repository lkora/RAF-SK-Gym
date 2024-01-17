//
//  EditGymRequest.swift
//  GymTeam
//
//  Created by Luka Korica on 1/17/24.
//

import Foundation

struct EditGymRequest: APIRequest {
    let endpoint: Endpoint = ManagerEndpoint.editGym
    let method = APIRequestMethod.post
    let body: Codable?
    var suffix: String?
    
    init(with new: Gym) {
        let encoder = JSONEncoder()
        body = try? encoder.encode(new)
    }
}
