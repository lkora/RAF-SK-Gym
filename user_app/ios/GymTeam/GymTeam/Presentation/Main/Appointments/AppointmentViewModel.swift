//
//  AppointmentViewModel.swift
//  GymTeam
//
//  Created by Luka Korica on 1/15/24.
//

import Foundation

final class AppointmentViewModel: ObservableObject {
    
    @Published var appointments: [Appointment]
    private var apiService: AuthorizedAPIService
    private var gymService: GymAPIService
    private var myUser: User


    init(appointments: [Appointment], apiService: AuthorizedAPIService, myUser: User) {
        self.appointments = appointments
        self.apiService = apiService
        self.myUser = myUser
        self.gymService = GymAPIService(apiService: apiService)
    }
    
    func handle(_ appointment: Appointment) {
        if let index = appointments.firstIndex(where: { $0.id == appointment.id }) {
            
            if appointment.isAvailable && !appointment.participants.contains(myUser) {
                gymService.makeAppointment(with: appointment.id) { result in
                    switch result {
                    case .success(_):
                        self.appointments[index].isAvailable.toggle()
                    case .failure(let failure):
                        debugPrint(failure)
                    }
                }
            } else {
                gymService.cancleAppointment(with: appointment.id) { result in
                    switch result {
                    case .success(_):
                        self.appointments[index].isAvailable.toggle()
                    case .failure(let failure):
                        debugPrint(failure)
                    }

                }
            }
        }
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
    
}
