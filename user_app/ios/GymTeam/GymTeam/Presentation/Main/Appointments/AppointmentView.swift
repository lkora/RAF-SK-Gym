//
//  ScheduleView.swift
//  GymTeam
//
//  Created by Luka Korica on 1/14/24.
//

import SwiftUI

struct AppointmentView: View {
    @State private var searchText = ""
    @State private var sortOption = 0
    @State var appointments: [Appointment] = [] // This should be your actual data
    
    var body: some View {
        NavigationStack {
            VStack {
                Picker("Sort by", selection: $sortOption) {
                    Text("Type").tag(0)
                    Text("Availability").tag(1)
                    Text("Group").tag(2)
                    Text("Starting Time").tag(3)
                }
                .pickerStyle(SegmentedPickerStyle())
                .padding()
                
                List {
                    ForEach(appointments.filter {
                        self.searchText.isEmpty ? true : $0.training.name.lowercased().contains(self.searchText.lowercased())
                    }.sorted(by: {
                        switch sortOption {
                        case 0: return $0.training.name < $1.training.name
                        case 1: return $0.isAvailable && !$1.isAvailable
                        case 2: return !$0.training.isGroup
                        case 3: return $0.date < $1.date
                        default: return false
                        }
                    })) { appointment in
                        AppointmentRow(appointment: appointment)
                    }
                }.searchable(text: $searchText)
                
            }
            .navigationTitle("Appointments")
        }
    }
}

struct AppointmentRow: View {
    var appointment: Appointment

    var body: some View {
        VStack(alignment: .leading) {
            Text(appointment.training.name)
                .font(.headline)
            Text("Type: \(appointment.training.isGroup ? "Group" : "Individual")")
            Text("Date: \(appointment.date)")
            Text("Available: \(appointment.isAvailable ? "Yes" : "No")")
            Button(appointment.isAvailable ? "Schedule" : "Cancel") {
                // TODO: Handle schedule or cancle here
            }
        }
    }
}


#Preview {
    AppointmentView(appointments: generateDummyAppointments())
}

func generateDummyTrainings() -> [Training] {
    return [
        Training(name: "Yoga", description: "A relaxing group training", numberOfTrainers: 2, price: 10.0, isGroup: true, maxParticipants: 12),
        Training(name: "Powerlifting", description: "An intense individual training", numberOfTrainers: 1, price: 20.0, isGroup: false),
        Training(name: "Pilates", description: "A group training focusing on core strength", numberOfTrainers: 3, price: 15.0, isGroup: true, maxParticipants: 10),
        Training(name: "Calisthenics", description: "A bodyweight training for individuals", numberOfTrainers: 1, price: 25.0, isGroup: false)
    ]
}

func generateDummyAppointments() -> [Appointment] {
    
    let dummyTrainings: [Training] = generateDummyTrainings()
    let dummyUsers: [User] = generateDummyUsers()
    var dummyAppointments: [Appointment] = []

    for i in 0..<20 {
        let training = dummyTrainings[i % dummyTrainings.count]
        let date = Calendar.current.date(byAdding: .day, value: i, to: Date())!
        let participants = Array(dummyUsers.prefix((i % dummyUsers.count) + 1))
        let isAvailable = i % 3 != 0 // Change this to create a more varied availability
        let appointment = Appointment(id: 0, training: training, date: date, participants: participants, isAvailable: isAvailable)
        dummyAppointments.append(appointment)
    }

    return dummyAppointments
}
