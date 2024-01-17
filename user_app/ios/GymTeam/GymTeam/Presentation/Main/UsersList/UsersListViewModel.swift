//
//  UsersListViewModel.swift
//  GymTeam
//
//  Created by Luka Korica on 1/17/24.
//

import Foundation

final class UsersListViewModel: ObservableObject {
    @Published var myUser: User
    @Published var users: [User] = []
    
    private var apiService: AuthorizedAPIService
    private var gymService: GymAPIService

    
    init(apiService: AuthorizedAPIService, myUser: User, users: [User] = []) {
        self.apiService = apiService
        self.gymService = GymAPIService(apiService: apiService)
        self.myUser = myUser
        self.users = users
    }
    
    func loadUsers() {
        gymService.getUsers { result in
            switch result {
            case .success(let users):
                self.users = users
            case .failure(let error):
                debugPrint(error)
            }
        }
    }
    
    func banUnban(_ user: User) {
        if let index = users.firstIndex(where: { $0.username == user.username }) {
            if users[index].isBanned {
                gymService.unbanUser(with: user.username) { result in
                    switch result {
                    case .success(_):
                        self.users[index].isBanned.toggle()
                    case .failure(let error):
                        debugPrint("Error unbanning user: \(error)")
                    }
                }
            } else {
                gymService.banUser(with: user.username) { result in
                    switch result {
                    case .success(_):
                        self.users[index].isBanned.toggle()
                    case .failure(let error):
                        debugPrint("Error banning user: \(error)")
                    }
                }
            }
        }
    }

    
    
}
