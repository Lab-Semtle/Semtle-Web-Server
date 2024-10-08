"""
API 개발 시 참고 : 비즈니스 로직 작성, control에서 호출
"""
# 호출할 모듈 추가
from src.api.v1.comment_exam.comm_exam_dto import UpdateComment, ReadComment, CreateComment, ReadCommentlist
from src.api.v1.comment_exam import comm_exam_dao


# Read
async def get_exam_sharing_board_comment(exam_sharing_board_no: int, skip: int) -> list[ReadCommentlist]:
    total, exam_sharing_board_comment_info = await comm_exam_dao.get_exam_sharing_board_comment(exam_sharing_board_no, skip)
    exam_sharing_board_comment_info = [ReadComment.from_orm(Comment).dict() for Comment in exam_sharing_board_comment_info]
    return total, exam_sharing_board_comment_info


# Create
async def create_exam_sharing_board_comment(exam_sharing_board_no: int, exam_sharing_board_comment_info: CreateComment) -> None:
    await comm_exam_dao.create_exam_sharing_board_comment(exam_sharing_board_no, exam_sharing_board_comment_info)
    
    
# Update
async def update_exam_sharing_board_comment(exam_sharing_board_no: int ,exam_sharing_board_comment_no: int, exam_sharing_board_comment_info: UpdateComment) -> None:
    await comm_exam_dao.update_exam_sharing_board_comment(exam_sharing_board_no, exam_sharing_board_comment_no, exam_sharing_board_comment_info)
    

# Delete
async def delete_exam_sharing_board_comment(exam_sharing_board_comment_no: int) -> None:
    await comm_exam_dao.delete_exam_sharing_board_comment(exam_sharing_board_comment_no)