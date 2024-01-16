//
//  RegistrationViewModel.swift
//  GymTeam
//
//  Created by Luka Korica on 1/16/24.
//


import Foundation
import Combine
import UIKit
import SwiftUI

final class RegistrationViewModel: ObservableObject {
    @Published var user = User(userType: .client, username: "", password: "", email: "", dob: Date(), firstName: "", lastName: "", memberCardNumber: nil, scheduledTrainings: nil, gymName: nil, employmentDate: nil)
        
    private var apiService: AuthorizedAPIService
    
    private var gymService: GymAPIService
    
    init(apiService: AuthorizedAPIService) {
        self.apiService = apiService
        self.gymService = GymAPIService(apiService: apiService)
    }
    
    func register() {
        if user.userType == .client {
            gymService.registerClient(params: RegisterClientParams(userName: user.username, password: user.password, email: user.email, birthDate: user.dob, firstName: user.firstName, lastName: user.lastName)) { result in
                switch result {
                case .success(_):
                    debugPrint("Sucessfully registered a Client!")
                case .failure(let failure):
                    debugPrint("Error: \(failure)")
                }
            }
        } else if user.userType == .manager {
            gymService.registerManager(params: RegisterManagerParams(userName: user.username, password: user.password, email: user.email, birthDate: user.dob, firstName: user.firstName, lastName: user.lastName, gymName: user.gymName ?? "", dateOfEmployment: user.employmentDate ?? Date())) { result in
                switch result {
                case .success(_):
                    debugPrint("Sucessfully registered a Manager!")
                case .failure(let failure):
                    debugPrint("Error: \(failure)")
                }
            }
        }
    }
    
}

