//
//  MainViewModel.swift
//  GymTeam
//
//  Created by Luka Korica on 1/17/24.
//

import Foundation
import Combine
import UIKit

final class MainViewModel: ObservableObject {
    @Published var myUser: User
    
    
    private var apiService: AuthorizedAPIService
    private var gymService: GymAPIService

    
    init(apiService: AuthorizedAPIService, myUser: User = User(userType: .client, username: "", password: "", email: "", birthDate: Date(), firstName: "", lastName: "")) {
        self.apiService = apiService
        self.gymService = GymAPIService(apiService: apiService)
        self.myUser = myUser
    }
    
    func loadMyUser() {
        gymService.getProfile { result in
            switch result {
            case .success(let user):
                self.myUser = user
            case .failure(let error):
                debugPrint(error)
            }
        }
    }
    
    
    
}
