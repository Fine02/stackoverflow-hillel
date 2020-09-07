# Stack Overflow #
The project is created based on the theory obtained during schooling. We took the site StackOverflow for the idea of the project.

Stack Overflow is one of the largest online communities for developers to learn and share their knowledge. The website provides a platform for its users to ask and answer questions, and through membership and active participation, to vote questions and answers up or down. Users can edit questions and answers in a fashion similar to a wiki.

### Stage of development ###

**Mandatory test coverage - 90%**

At the moment, the project is under development.
* **Done:**
  * Application architecture
  * Service layer
  * Adding Spring Boot 
  * Implemented DAO with Jooq
  * Simple UI (Spring MVC + thymeleaf)
* **Current stage:** add ***Hibernate*** instead of Jooq
* **Further:**
  * Rest
  * Angular
  * Spring Secyrity
  * AWS

### Requirements and Goals of the System ###
***Design a system with the following requirements:***
1.	Any non-member (guest) can search and view questions. However, to add or upvote a question, they have to become a member.
2.	Members should be able to post new questions.
3.	Members should be able to add an answer to an open question.
4.	Members can add comments to any question or answer.
5.	A member can upvote a question, answer or comment.
6.	Members can flag a question, answer or comment, for serious problems or moderator attention.
7.	Any member can add a bounty to their question to draw attention.
8.	Members will earn badges for being helpful.
9.	Members can vote to close a question; Moderators can close or reopen any question.
10.	Members can add tags to their questions. A tag is a word or phrase that describes the topic of the question.
11.	Members can vote to delete extremely off-topic or very low-quality questions.
12.	Moderators can close a question or undelete an already deleted question.
13.	The system should also be able to identify most frequently used tags in the questions.


### Use-case Diagram ###

***We have five main actors in our system:***
*	**Admin:** Mainly responsible for blocking or unblocking members.
*	**Guest:** All guests can search and view questions.
*	**Member:** Members can perform all activities that guests can, in addition to which they can add/remove questions, answers, and comments. Members can delete and un-delete their questions, answers or comments.
*	**Moderator:** In addition to all the activities that members can perform, moderators can close/delete/undelete any question.
*	**System:** Mainly responsible for sending notifications and assigning badges to members.

