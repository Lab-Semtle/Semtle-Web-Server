"""
API 개발 시 참고 : 프론트엔드에서 http 엔드포인트를 통해 호출되는 메서드
"""
# 기본적으로 추가
from typing import Optional
from fastapi import APIRouter, Depends, UploadFile, File
from fastapi.responses import FileResponse
from src.lib.status import Status, SU, ER
from src.lib.type import ResultType
import logging
import os


# 호출할 모듈 추가
from src.api.v1.board_study.study_dto import ReadBoard, ReadBoardlist, CreateBoard, UpdateBoard
from src.api.v1.board_study import study_svc

BASE_DIR = os.path.dirname('')
STATIC_DIR = os.path.join(BASE_DIR, 'images/study_board/')
SERVER_IMG_DIR = os.path.join('http://localhost:8000/', 'images/study_board/')


# 로깅 및 라우터 객체 생성 - 기본적으로 추가
logger = logging.getLogger(__name__)
router = APIRouter(prefix="/study_board", tags=["study_board"])

# 라우터 추가 시 현재는 src.api.v1.__init__.py에 생성하려는 라우터 추가해줘야 함.(수정 예정)


# Read List
@router.get(
    "/get_list",
    summary="스터디 게시판 게시물 전체 조회",
    description="- 스터디 게시판 게시물 전체 리스트 반환, 등록된 예제가 없는 경우 `[]` 반환",
    response_model=ReadBoardlist,
    responses=Status.docs(SU.SUCCESS, ER.NOT_FOUND)
)
# 함수명 get, post, update, delete 중 1택 + 목적에 맞게 이름 작성
async def get_study_board_list(page: int = 0):
    # 개발 중 logging 사용하고 싶을 때 이 코드 추가
    logger.info("----------스터디 게시판 전체 목록 조회----------")
    total, study_board_info = await study_svc.get_study_board_list(skip=page)
    return {
        'total': total,
        'Board_info': study_board_info
    }

# Read
@router.get(
    "/get",
    summary="스터디 게시판 특정 게시물 조회",
    description="- 스터디 게시판 특정 게시물 정보 반환, 등록된 예제가 없는 경우 `[]` 반환",
    response_model=ReadBoard,
    responses=Status.docs(SU.SUCCESS, ER.NOT_FOUND)
)
# 함수명 get, post, update, delete 중 1택 + 목적에 맞게 이름 작성
async def get_study_board(study_board_no: int = 0):
    # 개발 중 logging 사용하고 싶을 때 이 코드 추가
    logger.info("----------스터디 게시판 특정 게시물 조회----------")
    study_board_info = await study_svc.get_study_board(study_board_no)
    return study_board_info

# Image 
@router.get(
    "/images",
    summary="스터디 게시판 특정 게시물 이미지 조회",
    description="- 스터디 게시판 특정 게시물 이미지 반환, 등록된 예제가 없는 경우 `[]` 반환",
    response_model=list[str],
    responses=Status.docs(SU.SUCCESS, ER.NOT_FOUND)
)
# 함수명 get, post, update, delete 중 1택 + 목적에 맞게 이름 작성
async def get_Images_study_board(file_name: str = ""):
    # 개발 중 logging 사용하고 싶을 때 이 코드 추가
    logger.info("----------스터디 게시판 특정 게시물 이미지 조회----------")
    return FileResponse(''.join([STATIC_DIR,file_name]))


# Create
@router.post(
    "/",
    summary="스터디 게시판 신규 게시물 생성",
    description="- String-Form / String-Form / Integer-Field",
    # response_model=ResultType, # -> 코드 미완성, 주석처리
    responses=Status.docs(SU.CREATED, ER.DUPLICATE_RECORD, ER.FIELD_VALIDATION_ERROR)
)
async def create_study_board(
    study_board_info: Optional[CreateBoard]
):
    logger.info("----------스터디 게시판 신규 게시물 생성----------")
    study_board_no = await study_svc.create_study_board(study_board_info)
    return { "status": SU.CREATED, "Study_Board_No": study_board_no}


# # Create
# @router.post(
#     "/upload",
#     summary="입력 받은 데이터를 데이터베이스에 추가 (업로드한 파일 포함)",
#     description="- String-Form / String-Form / List[UploadFile]",
#     # response_model=ResultType, # -> 코드 미완성, 주석처리
#     responses=Status.docs(SU.CREATED, ER.DUPLICATE_RECORD, ER.FIELD_VALIDATION_ERROR)
# )
# async def upload_create_study_board(
#     title: str,
#     content: str,
#     file_name: list[UploadFile] = File(...),
#     db: AsyncSession = Depends(get_db)
# ):
#     logger.info("----------스터디 게시판 신규 게시물(파일 포함) 생성----------")
#     await study_svc.upload_create_study_board(title, content, file_name)
#     return SU.CREATED

# Create
@router.put(
    "/create_upload",
    summary="스터디 게시판 신규 게시물 이미지 생성",
    description="- List[UploadFile]",
    response_model=ResultType,
    responses=Status.docs(SU.CREATED, ER.DUPLICATE_RECORD, ER.FIELD_VALIDATION_ERROR)
)
async def upload_file_study_board(
    study_board_no: int,
    file_name: list[UploadFile] = File(...)
):
    logger.info("----------스터디 게시판 신규 게시물 이미지 생성----------")
    await study_svc.upload_file_study_board(study_board_no, file_name)
    return ResultType(status='success', message=SU.CREATED[1])


# Update
@router.put(
    "/",
    summary="스터디 게시판 기존 게시물 수정",
    description="- no가 일치하는 데이터의 title, content, view 수정",
    response_model=ResultType,
    responses=Status.docs(SU.SUCCESS, ER.DUPLICATE_RECORD)
)
async def update_study_board(
    study_board_no: int,
    study_board_info: Optional[UpdateBoard],
    select: bool = False
):
    logger.info("----------스터디 게시판 기존 게시물 수정----------")
    await study_svc.update_study_board(study_board_no, study_board_info, select=select)
    return ResultType(status='success', message=SU.SUCCESS[1])


# # Update
# @router.put(
#     "/upload",
#     summary="입력 받은 데이터로 변경 사항 수정(업로드한 파일 포함)",
#     description="- no가 일치하는 데이터의 title, content, file_name 수정",
#     responses=Status.docs(SU.CREATED, ER.DUPLICATE_RECORD)
# )
# async def upload_update_study_board(
#     study_board_no: int,  # JWT 토큰에서 id 가져오는 방식으로 변경, 이건 임시조치
#     title: str,
#     content: str,
#     file_name: list[UploadFile] = File(...),
#     db: AsyncSession = Depends(get_db)
# ):
#     logger.info("----------스터디 게시판 기존 게시물(파일 포함) 수정----------")
#     await study_svc.upload_update_study_board(study_board_no, title, content, file_name)
#     return SU.SUCCESS

# Update
@router.put(
    "/update_upload",
    summary="스터디 게시판 기존 게시물 이미지 수정",
    description="- List[UploadFile]",
    response_model=ResultType,
    responses=Status.docs(SU.SUCCESS, ER.DUPLICATE_RECORD)
)
async def upload_update_file_study_board(
    study_board_no: int,
    file_name: list[UploadFile] = File(...),
    select: bool = False
):
    logger.info("----------스터디 게시판 기존 게시물 이미지 수정----------")
    await study_svc.upload_update_file_study_board(study_board_no, file_name, select=select)
    return ResultType(status='success', message=SU.SUCCESS[1])


# Delete
@router.delete(
    "/",
    summary="스터디 게시판 게시물 삭제",
    description="- study_board_no가 일치하는 데이터 삭제",
    response_model=ResultType,
    responses=Status.docs(SU.SUCCESS, ER.DUPLICATE_RECORD),
)
async def delete_study_board(
    study_board_no: int # JWT 토큰에서 id 가져오는 방식으로 변경, 임시조치
):
    await study_svc.delete_study_board(study_board_no)
    return ResultType(status='success', message=SU.SUCCESS[1])


# sort title
@router.get(
    "/sort_title",
    summary="스터디 게시판 게시물 제목 정렬",
    description="- 스터디 게시판 게시물 제목을 가나다순으로 정렬하여 반환, 등록된 예제가 없는 경우 `[]` 반환",
    response_model=ReadBoardlist,
    responses=Status.docs(SU.SUCCESS, ER.NOT_FOUND)
)
# 함수명 get, post, update, delete 중 1택 + 목적에 맞게 이름 작성
async def sort_study_board(page: int = 0, select: int = 0):
    # 개발 중 logging 사용하고 싶을 때 이 코드 추가
    logger.info("----------스터디 게시판 제목 가나다순 정렬----------")
    total, study_board_info = await study_svc.sort_study_board(skip=page, select=select)
    return {
        'total': total,
        'Board_info': study_board_info
    }