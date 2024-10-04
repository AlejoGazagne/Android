package com.project.kotlincomposeapp.data.model

data class Event(
    val name: String,
    val date: String,
    val location: String,
    val image: String,
    val capacity: Int,
    val organizer: String
) {
    companion object {
        fun getEvents(): List<Event> {
            return listOf(
                Event(
                    "Tech Conference 2024",
                    "2024-11-12",
                    "Buenos Aires, Argentina",
                    "https://images.unsplash.com/photo-1542744095-fcf48d80b0fd",
                    500,
                    "Tech Corp"
                ),
                Event(
                    "Artificial Intelligence Summit",
                    "2024-10-20",
                    "San Francisco, USA",
                    "https://images.unsplash.com/photo-1504384308090-c894fdcc538d",
                    3000,
                    "AI Innovators"
                ),
                Event(
                    "Córdoba Developer Meetup",
                    "2024-12-05",
                    "Córdoba, Argentina",
                    "https://images.unsplash.com/photo-1560692901-45c9a2e443db",
                    150,
                    "Dev Community Córdoba"
                ),
                Event(
                    "Cybersecurity Expo",
                    "2024-11-30",
                    "London, UK",
                    "https://images.unsplash.com/photo-1498050108023-c5249f4df085",
                    2000,
                    "Security World"
                ),
                Event(
                    "Mobile App Hackathon",
                    "2024-10-15",
                    "Berlin, Germany",
                    "https://images.unsplash.com/photo-1536104968055-4d61aa56f46a",
                    400,
                    "Hack Club Europe"
                ),
                Event(
                    "IoT World Congress",
                    "2024-11-10",
                    "Barcelona, Spain",
                    "https://images.unsplash.com/photo-1487058792275-0ad4aaf24ca7",
                    6000,
                    "IoT Innovators"
                ),
                Event(
                    "Data Science Bootcamp",
                    "2024-12-01",
                    "New York, USA",
                    "https://images.unsplash.com/photo-1551836022-d5d88e9218df",
                    250,
                    "Data Wizards"
                ),
                Event(
                    "Blockchain Revolution",
                    "2024-11-18",
                    "Dubai, UAE",
                    "https://images.unsplash.com/photo-1518770660439-4636190af475",
                    1000,
                    "Crypto Leaders"
                ),
                Event(
                    "Web3.0 Summit",
                    "2024-10-25",
                    "Tokyo, Japan",
                    "https://images.unsplash.com/photo-1581091870622-cf04d1abfda7",
                    1500,
                    "Web Innovators"
                ),
                Event(
                    "Open Source Day",
                    "2024-11-03",
                    "Sao Paulo, Brazil",
                    "https://images.unsplash.com/photo-1504799563364-170b14c0e35b",
                    500,
                    "Open Source Foundation"
                ),
                Event(
                    "Python Dev Week",
                    "2024-12-10",
                    "Mexico City, Mexico",
                    "https://images.unsplash.com/photo-1544197150-b99a580bb7a8",
                    200,
                    "Pythonistas Unidos"
                ),
                Event(
                    "UX/UI Design Conference",
                    "2024-11-22",
                    "Paris, France",
                    "https://images.unsplash.com/photo-1504384308090-c894fdcc538d",
                    3000,
                    "Designers Collective"
                ),
                Event(
                    "Game Developers Forum",
                    "2024-10-27",
                    "Los Angeles, USA",
                    "https://images.unsplash.com/photo-1518770660439-4636190af475",
                    5000,
                    "GameDev Network"
                ),
                Event(
                    "Green Tech Expo",
                    "2024-12-15",
                    "Stockholm, Sweden",
                    "https://images.unsplash.com/photo-1521737604893-d14cc237f11d",
                    1200,
                    "Sustainable Tech Group"
                ),
                Event(
                    "JavaScript Nation",
                    "2024-11-28",
                    "Toronto, Canada",
                    "https://images.unsplash.com/photo-1517245386807-bb43f82c33c4",
                    800,
                    "JS Enthusiasts"
                ),
                Event(
                    "Machine Learning Camp",
                    "2024-12-08",
                    "Melbourne, Australia",
                    "https://images.unsplash.com/photo-1536104968055-4d61aa56f46a",
                    450,
                    "ML Innovators"
                ),
                Event(
                    "Big Data Symposium",
                    "2024-11-05",
                    "Singapore",
                    "https://images.unsplash.com/photo-1521737604893-d14cc237f11d",
                    2200,
                    "Data Science Global"
                ),
                Event(
                    "Virtual Reality Expo",
                    "2024-12-03",
                    "Los Angeles, USA",
                    "https://images.unsplash.com/photo-1518770660439-4636190af475",
                    3000,
                    "VR World"
                ),
                Event(
                    "Robotics Summit",
                    "2024-11-20",
                    "Seoul, South Korea",
                    "https://images.unsplash.com/photo-1536104968055-4d61aa56f46a",
                    1800,
                    "Automation Experts"
                ),
                Event(
                    "Quantum Computing Workshop",
                    "2024-12-02",
                    "Zurich, Switzerland",
                    "https://images.unsplash.com/photo-1518770660439-4636190af475",
                    100,
                    "Quantum Pioneers"
                )
            )
        }
    }
}
