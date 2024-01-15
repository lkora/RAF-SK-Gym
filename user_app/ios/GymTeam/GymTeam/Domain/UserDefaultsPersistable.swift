//
//  UserDefaultsPersistable.swift
//  GymTeam
//
//  Created by Luka Korica on 1/14/24.
//

import Foundation

protocol DefaultInitializable {
  init()
}

protocol UserDefaultsPeristable: Codable, DefaultInitializable {
    static var key: String {get}
}


struct UserDefaultsPersister<A: UserDefaultsPeristable> {
    static func save(_ value: A) {
        let encoder = JSONEncoder()
        if let encoded = try? encoder.encode(value) {
            UserDefaults.groupShared.set(encoded, forKey: A.key)
        }
    }

    static func load() -> A {
        guard let savedSettingsData = UserDefaults.groupShared.object(forKey: A.key) as? Data else {
            return A()
        }

        let decoder = JSONDecoder()
        let loadedSettings = try? decoder.decode(A.self, from: savedSettingsData)
        return loadedSettings ?? A()
    }
}


extension UserDefaultsPeristable {
    func save() {
        UserDefaultsPersister.save(self)
    }

    static func load() -> Self {
        return UserDefaultsPersister.load()
    }
}


extension UserDefaults {
    static let groupShared = UserDefaults(suiteName: "group.com.lukakorica.GymTeam")!
    
}
