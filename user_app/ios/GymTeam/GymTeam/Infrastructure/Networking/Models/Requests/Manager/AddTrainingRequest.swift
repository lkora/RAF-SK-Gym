//
//  AddTrainingRequest.swift
//  GymTeam
//
//  Created by Luka Korica on 1/17/24.
//

import Foundation

struct AddTrainingRequest: APIRequest {
    let endpoint: Endpoint = ManagerEndpoint.newTraining
    let method = APIRequestMethod.post
    let body: Codable?
    var suffix: String?
    
    init(new training: Training) {
        let encoder = JSONEncoder()
        body = try? encoder.encode(training)
    }
}
