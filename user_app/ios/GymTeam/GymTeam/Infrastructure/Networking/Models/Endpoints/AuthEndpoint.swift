//
//  AuthEndpoint.swift
//  GymTeam
//
//  Created by Luka Korica on 1/16/24.
//

import Foundation

enum AuthEndpoint: Endpoint {
    case login
    case registerClient
    case registerManager
    
    var basePath: String {
        "/auth"
    }
    
    var path: String {
        switch self {
        case .login:
            return "/login"
        case .registerClient:
            return "/register/client"
        case .registerManager:
            return "/register/manager"
        }
    }
}
