//
//  SensitiveDataPersistency.swift
//  GymTeam
//
//  Created by Luka Korica on 1/13/24.
//

import Foundation

protocol SensitiveDataPersistency {
    func save(_ data: Data, service: String, account: String)
    func read(service: String, account: String) -> Data?
    func delete(service: String, account: String)
}
