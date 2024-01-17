//
//  ManagerEndpoint.swift
//  GymTeam
//
//  Created by Luka Korica on 1/17/24.
//

import Foundation

enum ManagerEndpoint: Endpoint {
    case editGym
    case newTraining
    case deleteTraining
    
    var basePath: String {
        "/manager"
    }
    
    var path: String {
        switch self {
        case .editGym:
            return "/edit-gym"
        case .newTraining:
            return "/new-training"
        case .deleteTraining:
            return "/delete-training"
        }
    }
}
