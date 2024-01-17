//
//  ProfileViewModel.swift
//  GymTeam
//
//  Created by Luka Korica on 1/17/24.
//

import Foundation


final class ProfileViewModel: ObservableObject {
    @Published var myUser: User
    
    private var apiService: AuthorizedAPIService
    private var gymService: GymAPIService

    
    init(apiService: AuthorizedAPIService, myUser: User) {
        self.apiService = apiService
        self.gymService = GymAPIService(apiService: apiService)
        self.myUser = myUser
    }
    
    
    func saveProfile() {
        gymService.editProfile(params: EditProfileParams(userName: myUser.username, password: myUser.password, email: myUser.email, dateOfBirth: myUser.birthDate, firstName: myUser.firstName, lastName: myUser.lastName)) { response in
            switch response {
            case .success(_):
                break
            case .failure(let failure):
                debugPrint(failure)
            }
        }
    }
    
}
