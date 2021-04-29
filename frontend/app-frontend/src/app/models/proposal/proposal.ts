export class Proposal {
  id: string;
  authorId: string;
  topic: string;
  description: string;
  assigned: number;
  votes: number;
  assignedUsers: string[];

  constructor() {
    this.id = '';
    this.authorId = '';
    this.topic = '';
    this.description = '';
    this.votes = 0;
    this.assigned = 0;
    this.assignedUsers = String[''];
  }
}


