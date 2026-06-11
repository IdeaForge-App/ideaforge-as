package pe.edu.upc.ideaforgev1.core.data.fake

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshots.SnapshotStateList
import pe.edu.upc.ideaforgev1.features.exploration.domain.model.IdeaCard

data class FakeUserProfile(
    val fullName: String,
    val headline: String,
    val bio: String,
    val experienceLevel: String,
    val skills: List<String>,
    val interests: List<String>
)

data class FakeApplication(
    val id: Long,
    val ideaTitle: String,
    val requestedRole: String,
    val status: String,
    val message: String
)

data class FakeMessage(
    val id: Long,
    val senderName: String,
    val content: String,
    val sentAt: String,
    val isMine: Boolean
)

class FakeConversation(
    val id: Long,
    val ideaTitle: String,
    val participantName: String,
    val participantHeadline: String,
    lastMessage: String,
    updatedAt: String,
    unreadCount: Int,
    val messages: SnapshotStateList<FakeMessage>
) {
    var lastMessage by mutableStateOf(lastMessage)
    var updatedAt by mutableStateOf(updatedAt)
    var unreadCount by mutableStateOf(unreadCount)
}

object FakeData {

    private var nextIdeaId = 5L

    val profile = FakeUserProfile(
        fullName = "Eduardo Cossar",
        headline = "Software Engineering Student",
        bio = "I enjoy building digital products, joining early-stage ideas and collaborating with creative teams.",
        experienceLevel = "Junior",
        skills = listOf("Kotlin", "UX/UI", "Backend", "Android", "Business"),
        interests = listOf("Technology", "Education", "Startups", "Product Design")
    )

    val ideas = mutableStateListOf(
        IdeaCard(
            id = 1,
            title = "NovaLearn",
            description = "AI-powered study platform for personalized learning.",
            category = "Education",
            membersCount = 24,
            roles = listOf("Developer", "Design", "Marketing"),
            actionLabel = "View"
        ),
        IdeaCard(
            id = 2,
            title = "GreenPulse",
            description = "Smart solution to track and reduce your carbon footprint.",
            category = "Sustainability",
            membersCount = 18,
            roles = listOf("Tech", "Developer", "Design"),
            actionLabel = "Apply"
        ),
        IdeaCard(
            id = 3,
            title = "TeamSpark",
            description = "Collaborative workspace for high-performing remote teams.",
            category = "Business",
            membersCount = 31,
            roles = listOf("Design", "Marketing", "Developer"),
            actionLabel = "View"
        ),
        IdeaCard(
            id = 4,
            title = "EcoMarket",
            description = "Marketplace for sustainable products created by students.",
            category = "Commerce",
            membersCount = 12,
            roles = listOf("Business", "Marketing", "Tech"),
            actionLabel = "Apply"
        )
    )

    val savedIdeaIds = mutableStateListOf(1L, 2L)

    val applications = mutableStateListOf(
        FakeApplication(
            id = 1,
            ideaTitle = "GreenPulse",
            requestedRole = "Developer",
            status = "Pending",
            message = "I would like to help build the Android experience."
        ),
        FakeApplication(
            id = 2,
            ideaTitle = "TeamSpark",
            requestedRole = "UX/UI Designer",
            status = "Accepted",
            message = "I can support the product design and wireframes."
        ),
        FakeApplication(
            id = 3,
            ideaTitle = "EcoMarket",
            requestedRole = "Business",
            status = "Rejected",
            message = "I am interested in helping with validation and strategy."
        )
    )

    val conversations = mutableStateListOf(
        FakeConversation(
            id = 1,
            ideaTitle = "GreenPulse",
            participantName = "Sofia Martinez",
            participantHeadline = "Product Designer",
            lastMessage = "Great, we can review the prototype tomorrow.",
            updatedAt = "10:45 AM",
            unreadCount = 2,
            messages = mutableStateListOf(
                FakeMessage(
                    id = 1,
                    senderName = "Sofia Martinez",
                    content = "Hi Eduardo! I saw your application for GreenPulse.",
                    sentAt = "10:20 AM",
                    isMine = false
                ),
                FakeMessage(
                    id = 2,
                    senderName = "You",
                    content = "Hi Sofia! Yes, I would like to collaborate as a developer.",
                    sentAt = "10:28 AM",
                    isMine = true
                ),
                FakeMessage(
                    id = 3,
                    senderName = "Sofia Martinez",
                    content = "Great, we can review the prototype tomorrow.",
                    sentAt = "10:45 AM",
                    isMine = false
                )
            )
        ),
        FakeConversation(
            id = 2,
            ideaTitle = "TeamSpark",
            participantName = "Luis Herrera",
            participantHeadline = "Startup Founder",
            lastMessage = "Your UX/UI experience could help us a lot.",
            updatedAt = "Yesterday",
            unreadCount = 0,
            messages = mutableStateListOf(
                FakeMessage(
                    id = 1,
                    senderName = "Luis Herrera",
                    content = "Thanks for applying to TeamSpark.",
                    sentAt = "Yesterday",
                    isMine = false
                ),
                FakeMessage(
                    id = 2,
                    senderName = "You",
                    content = "I liked the idea. I can help with wireframes and user flows.",
                    sentAt = "Yesterday",
                    isMine = true
                ),
                FakeMessage(
                    id = 3,
                    senderName = "Luis Herrera",
                    content = "Your UX/UI experience could help us a lot.",
                    sentAt = "Yesterday",
                    isMine = false
                )
            )
        ),
        FakeConversation(
            id = 3,
            ideaTitle = "NovaLearn",
            participantName = "Camila Torres",
            participantHeadline = "AI Enthusiast",
            lastMessage = "Let's define the first MVP features.",
            updatedAt = "Mon",
            unreadCount = 1,
            messages = mutableStateListOf(
                FakeMessage(
                    id = 1,
                    senderName = "Camila Torres",
                    content = "I think NovaLearn could start with flashcards and quizzes.",
                    sentAt = "Mon",
                    isMine = false
                ),
                FakeMessage(
                    id = 2,
                    senderName = "You",
                    content = "Yes, and later we can add personalized recommendations.",
                    sentAt = "Mon",
                    isMine = true
                ),
                FakeMessage(
                    id = 3,
                    senderName = "Camila Torres",
                    content = "Let's define the first MVP features.",
                    sentAt = "Mon",
                    isMine = false
                )
            )
        )
    )

    fun getIdeaById(ideaId: Long): IdeaCard? {
        return ideas.find { it.id == ideaId }
    }

    fun getSavedIdeas(): List<IdeaCard> {
        return ideas.filter { savedIdeaIds.contains(it.id) }
    }

    fun getCreatedIdeas(): List<IdeaCard> {
        return ideas.take(2)
    }

    fun getConversationById(conversationId: Long): FakeConversation? {
        return conversations.find { it.id == conversationId }
    }

    fun updateConversationLastMessage(
        conversationId: Long,
        message: String
    ) {
        val conversation = getConversationById(conversationId)
        conversation?.lastMessage = message
        conversation?.updatedAt = "Now"
    }

    fun addIdea(
        title: String,
        description: String,
        category: String,
        roles: List<String>
    ) {
        ideas.add(
            0,
            IdeaCard(
                id = nextIdeaId++,
                title = title,
                description = description,
                category = category,
                membersCount = 1,
                roles = roles,
                actionLabel = "View"
            )
        )
    }
}
