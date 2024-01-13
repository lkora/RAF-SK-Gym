//
//  Endpoint.swift
//  GymTeam
//
//  Created by Luka Korica on 1/13/24.
//

import Foundation

protocol Endpoint {
    var basePath: String { get }
    var path: String { get }
    var fullPath: String { get }
}

extension Endpoint {
    
    var fullPath: String {
        basePath + path
    }
}
