//
//  User.swift
//  GymTeam
//
//  Created by Luka Korica on 1/14/24.
//

import Foundation

struct User: Identifiable, Codable {
    var id: String { return username }
    var userType: UserType
    var username: String
    var password: String
    var email: String
    var birthDate: Date
    var firstName: String
    var lastName: String
    var isBanned: Bool = false
    var memberCardNumber: String?
    var scheduledTrainings: Int?
    var gymName: String?
    var employmentDate: Date?
}



extension User: Equatable {
    static func ==(lhs: User, rhs: User) -> Bool {
        return lhs.id == rhs.id &&
               lhs.userType == rhs.userType &&
               lhs.username == rhs.username &&
               lhs.password == rhs.password &&
               lhs.email == rhs.email &&
               lhs.birthDate == rhs.birthDate &&
               lhs.firstName == rhs.firstName &&
               lhs.lastName == rhs.lastName &&
               lhs.isBanned == rhs.isBanned &&
               lhs.memberCardNumber == rhs.memberCardNumber &&
               lhs.scheduledTrainings == rhs.scheduledTrainings &&
               lhs.gymName == rhs.gymName &&
               lhs.employmentDate == rhs.employmentDate
    }
}
