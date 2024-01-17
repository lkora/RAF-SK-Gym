//
//  ProfileEndpoint.swift
//  GymTeam
//
//  Created by Luka Korica on 1/17/24.
//

import Foundation

enum ProfileEndpoint: Endpoint {
    case getProfile
    case editProfile
    
    var basePath: String {
        "/profile"
    }
    
    var path: String {
        switch self {
        case .getProfile:
            return ""
        case .editProfile:
            return "/edit"
        }
    }
    
}
