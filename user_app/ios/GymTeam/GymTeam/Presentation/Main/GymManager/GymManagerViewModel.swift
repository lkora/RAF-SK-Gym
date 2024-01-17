//
//  GymManagerViewModel.swift
//  GymTeam
//
//  Created by Luka Korica on 1/17/24.
//

import Foundation

final class GymManagerViewModel: ObservableObject {
    
    @Published var appointments: [Appointment]
    private var apiService: AuthorizedAPIService
    private var gymService: GymAPIService
    @Published var gym: Gym
    private var myUser: User


    init(appointments: [Appointment], gym: Gym, apiService: AuthorizedAPIService, myUser: User) {
        self.appointments = appointments
        self.apiService = apiService
        self.myUser = myUser
        self.gym = gym
        self.gymService = GymAPIService(apiService: apiService)
    }
    
    
    func loadAppointments() {
        gymService.getSchedule { result in
            switch result {
            case .success(let appointments):
                self.appointments = appointments
            case .failure(let failure):
                debugPrint(failure)
            }
        }
    }
        
    func cancleAppointment(_ appointment: Appointment) {
        gymService.cancleAppointment(with: appointment.id) { result in
            switch result {
            case .success(_):
                self.appointments.removeAll(where: {$0.id == appointment.id})
            case .failure(let failure):
                debugPrint(failure)
            }
        }
    }
    
}
