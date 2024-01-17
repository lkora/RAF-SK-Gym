//
//  MakeAppointmentRequest.swift
//  GymTeam
//
//  Created by Luka Korica on 1/17/24.
//

import Foundation

struct MakeAppointmentRequest: APIRequest {
    let endpoint: Endpoint = ScheduleEndpoint.makeAppointment
    let method = APIRequestMethod.post
    let body: Codable?
    var suffix: String?
    
    init(params: MakeAppointmentParams) {
        let encoder = JSONEncoder()
        body = try? encoder.encode(params)
    }
}


struct MakeAppointmentParams: Codable {
    let id: Int
}
