//
//  ScheduleEndpoint.swift
//  GymTeam
//
//  Created by Luka Korica on 1/17/24.
//

import Foundation

enum ScheduleEndpoint: Endpoint {
    case getSchedule
    case cancelAppointment
    case makeAppointment
    
    var basePath: String {
        "/schedule"
    }
    
    var path: String {
        switch self {
        case .getSchedule:
            return ""
        case .cancelAppointment:
            return "/cancel-appointment"
        case .makeAppointment:
            return "/make-appointment"
        }
    }
    
}

