//
//  CacheTokenHandler.swift
//  GymTeam
//
//  Created by Luka Korica on 1/13/24.
//

import Foundation

final class CacheTokenHandler: TokenHandler {
    
    private let accountName = "gym_team"
    private let serviceName: String
    
    init(serviceName: String) {
        self.serviceName = serviceName
    }
    
    func save(token: String) {
        let data = Data(token.utf8)
        KeychainHelper.standard.save(data, service: serviceName, account: accountName)
    }
    
    func getToken() -> String? {
        guard let data = KeychainHelper.standard.read(service: serviceName, account: accountName) else { return nil }
        return String(data: data, encoding: .utf8)
    }
    
    func removeToken() {
        KeychainHelper.standard.delete(service: serviceName, account: accountName)
    }
    
}

