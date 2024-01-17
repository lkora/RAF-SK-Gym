//
//  ManagerEndpoint.swift
//  GymTeam
//
//  Created by Luka Korica on 1/17/24.
//

import Foundation

enum ManagerEndpoint: Endpoint {
    case editGym
    
    var basePath: String {
        "/manager"
    }
    
    var path: String {
        switch self {
        case .editGym:
            return "/edit-gym"
        }
    }
}
