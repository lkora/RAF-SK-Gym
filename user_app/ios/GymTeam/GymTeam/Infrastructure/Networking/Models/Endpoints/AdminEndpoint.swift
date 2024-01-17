//
//  AdminEndpoint.swift
//  GymTeam
//
//  Created by Luka Korica on 1/17/24.
//

import Foundation

enum AdminEndpoint: Endpoint {
    case banUser
    case unbanUser
    case users
    
    var basePath: String {
        "/admin"
    }
    
    var path: String {
        switch self {
        case .banUser:
            return "/ban/"
        case .unbanUser:
            return "/unban/"
        case .users:
            return "/users"
        }
    }
}
