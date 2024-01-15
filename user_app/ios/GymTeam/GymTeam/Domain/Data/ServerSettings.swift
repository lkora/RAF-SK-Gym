//
//  SereverSettings.swift
//  GymTeam
//
//  Created by Luka Korica on 1/14/24.
//

import Foundation

struct ServerSettings: UserDefaultsPeristable {
    static var key: String = "ServerSettings"
    
    private var parameters: ServerConfigurationParameters?
    init() {
        parameters = ServerConfigurationParameters(baseServerUrl: "")
    }
    
    
    static var baseServerUrl: String? {
        return self.load().parameters?.baseServerUrl
    }
        
    static func updateBaseServerUrl(newState: String) {
        var settings = self.load()
        if let _ = settings.parameters {
            settings.parameters?.baseServerUrl = newState
            settings.save()
        }
    }

}

struct ServerConfigurationParameters: Codable {
    var baseServerUrl: String
}
