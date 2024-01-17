//
//  Appointment.swift
//  GymTeam
//
//  Created by Luka Korica on 1/15/24.
//

import Foundation

struct Training: Codable {
    var name: String
    var description: String
    var numberOfTrainers: Int
    var price: Double
    var isGroup: Bool
    var maxParticipants: Int?
}

struct Appointment: Codable, Identifiable {
    var id: Int
    var training: Training
    var date: Date
    var participants: [User]                // TODO: This may be optional, i dont really need it except to show maybe more details and to know how many users have booked the appointment. Not the most performant way of doing stuff
    var isAvailable: Bool
}
