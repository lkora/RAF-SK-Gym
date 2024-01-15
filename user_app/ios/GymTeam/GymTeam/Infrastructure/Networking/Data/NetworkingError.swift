//
//  NetworkingError.swift
//  GymTeam
//
//  Created by Luka Korica on 1/13/24.
//

import Foundation

enum NetworkingError: Error {
    case malformedURL
    case missingData
    
    // Status code
    case unauthorized
    case serverError
    case unhandledResponseCode
    
    static func parseStatusCode(_ code: Int) -> NetworkingError {
        switch code {
        case 401: return .unauthorized
        case 500...: return .serverError
        default: return .unhandledResponseCode
        }
    }
}
