//
//  Appointment.swift
//  GymTeam
//
//  Created by Luka Korica on 1/15/24.
//

import Foundation

struct Training {
    var name: String
    var description: String
    var numberOfTrainers: Int
    var price: Double
    var isGroup: Bool
    var maxParticipants: Int?
}

struct Appointment: Identifiable {
    let id = UUID()
    var training: Training
    var date: Date
    var participants: [User]
    var isAvailable: Bool
}
