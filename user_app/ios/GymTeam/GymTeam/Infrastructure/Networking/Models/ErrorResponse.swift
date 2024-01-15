//
//  ErrorResponse.swift
//  GymTeam
//
//  Created by Luka Korica on 1/13/24.
//

import Foundation

struct ErrorResponse: Codable, LocalizedError {
    let message: String
}
