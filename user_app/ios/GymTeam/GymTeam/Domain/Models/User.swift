//
//  User.swift
//  GymTeam
//
//  Created by Luka Korica on 1/14/24.
//

import Foundation

struct User: Identifiable {
    var id = UUID()
    var userType: UserType
    var username: String
    var password: String
    var email: String
    var dob: Date
    var firstName: String
    var lastName: String
    var isBanned: Bool = false
    var memberCardNumber: String?
    var scheduledTrainings: Int?
    var gymName: String?
    var employmentDate: Date?
}

