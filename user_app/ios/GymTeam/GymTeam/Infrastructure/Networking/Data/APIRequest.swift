//
//  APIRequest.swift
//  GymTeam
//
//  Created by Luka Korica on 1/13/24.
//

import Foundation

protocol APIRequest {
    var endpoint: Endpoint { get }
    var method: APIRequestMethod { get }
    func urlRequest(baseURLString: String) -> URLRequest?
    var body: Codable? { get }
}

extension APIRequest {
    func urlRequest(baseURLString: String) -> URLRequest? {
        guard var baseURL = URL(string: baseURLString) else {
            return nil
        }
        baseURL.appendPathComponent("/api")
        baseURL.appendPathComponent(endpoint.fullPath)
        
        var urlRequest = URLRequest(url: baseURL)
        urlRequest.setValue("application/json", forHTTPHeaderField: "Content-Type")
        urlRequest.setValue("application/json", forHTTPHeaderField: "Accept")
        urlRequest.httpMethod = method.rawValue
        
        if let body = body as? Data {
            urlRequest.httpBody = body
        }
        
        return urlRequest
    }
}
