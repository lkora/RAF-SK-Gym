//
//  GetScheduleRequest.swift
//  GymTeam
//
//  Created by Luka Korica on 1/17/24.
//

import Foundation

struct GetScheduleRequest: APIRequest {
    
    let endpoint: Endpoint = ScheduleEndpoint.getSchedule
    let method = APIRequestMethod.get
    let body: Codable? = nil
    var suffix: String? = nil
    
}
