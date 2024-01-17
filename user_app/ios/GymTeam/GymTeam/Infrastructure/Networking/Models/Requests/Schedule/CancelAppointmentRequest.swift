//
//  CancelScheduleRequest.swift
//  GymTeam
//
//  Created by Luka Korica on 1/17/24.
//

import Foundation


struct CancelAppointmentRequest: APIRequest {
    let endpoint: Endpoint = ScheduleEndpoint.cancelAppointment
    let method = APIRequestMethod.post
    let body: Codable?
    var suffix: String?
    
    init(params: CancelAppointmentParams) {
        let encoder = JSONEncoder()
        body = try? encoder.encode(params)
    }
}


struct CancelAppointmentParams: Codable {
    let id: Int
}
