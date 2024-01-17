//
//  UserType.swift
//  GymTeam
//
//  Created by Luka Korica on 1/14/24.
//

import Foundation

enum UserType: String, Codable {
    case client
    case manager
    case admin
    
    var name: String {
        switch self {
        case .admin: return "Administrator"
        case .client: return "Client"
        case .manager: return "Manager"
        }
    }
}
