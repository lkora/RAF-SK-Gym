//
//  LoginViewModel.swift
//  GymTeam
//
//  Created by Luka Korica on 1/16/24.
//

import Foundation
import Combine
import UIKit

final class LoginViewModel: ObservableObject {
    @Published var username: String
    @Published var password: String
    @Published var serverURL: String {
        didSet {
            ServerSettings.updateBaseServerUrl(newState: serverURL)
        }
    }
    
    @Published var isLoggedIn: Bool = false
    
    var apiService: AuthorizedAPIService {
        let service = AlamofireAPIService(baseUrlString: serverURL)
        let logoutService = AutorizedAPIServiceDecorator(decoratee: service)
        
        if let appDelegate = UIApplication.shared.delegate as? AppDelegate {
            logoutService.delegate = appDelegate
        }
        return logoutService
    }

    
    init(username: String = "", password: String = "", serverURL: String = "") {
        self.username = username
        self.password = password
        self.serverURL = serverURL
    }
    
    func login() {
        let gymApiService = GymAPIService(apiService: apiService)
        gymApiService.login(username: username, password: password) { [weak self] response in
            switch response {
            case .success(let token):
                self?.apiService.save(token: token.jwt)
                self?.isLoggedIn = true
            case .failure(let error):
                debugPrint("Error: \(error)")
            }
        }
    }
    
}
