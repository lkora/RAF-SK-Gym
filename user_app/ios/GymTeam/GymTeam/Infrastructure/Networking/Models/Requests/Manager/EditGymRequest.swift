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
    
    init(params: EditGymParams) {
        let encoder = JSONEncoder()
        body = try? encoder.encode(params)
    }
}


struct EditGymParams: Codable {
    var id: Int
    var name: String
    var description: String
    var numberOfTrainers: Int
}
