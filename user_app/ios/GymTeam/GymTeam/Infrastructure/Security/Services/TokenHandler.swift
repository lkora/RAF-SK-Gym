//
//  TokenHandler.swift
//  GymTeam
//
//  Created by Luka Korica on 1/13/24.
//

import Foundation

protocol StoreTokenHandler {
    func save(token: Token)
}

protocol RemoveTokenHandler {
    func removeToken()
}

protocol GetTokenHandler {
    func getToken() -> String?
}

protocol TokenHandler: StoreTokenHandler, RemoveTokenHandler, GetTokenHandler { }

extension TokenHandler {
    
    var hasToken: Bool {
        getToken() != nil
    }
}

