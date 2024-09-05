"""
API 개발 시 참고 : 비즈니스 로직 작성, control에서 호출
"""
# 기본적으로 추가
from sqlalchemy import select, update, insert, delete, func
from sqlalchemy.ext.asyncio import AsyncSession
from datetime import datetime, timezone

from src.api.v1.comment_free import comm_free_dao
from src.api.v1.board_free.free_dto import UpdateBoard, ReadBoard, CreateBoard, ReadBoardlist
from src.database.models import Free_Board
from src.database.session import rdb


# Read List
@rdb.dao()
async def get_free_board_list(skip: int, db: AsyncSession) -> tuple[int, list[ReadBoardlist]]:
    result = await db.execute(select(Free_Board).order_by(Free_Board.Board_no.desc()).offset(skip*10).limit(10))
    free_board_info = result.scalars().all()
    total = await db.execute(select(func.count(Free_Board.Board_no)))
    total = total.scalar()
    return total, free_board_info

# Read
@rdb.dao()
async def get_free_board(free_board_no: int, db: AsyncSession) -> ReadBoard:
    result = await db.execute(select(Free_Board).filter(Free_Board.Board_no == free_board_no))
    free_board_info = result.scalars().first()
    return free_board_info


# Create
@rdb.dao(transactional=True)
async def create_free_board(free_board_info: CreateBoard, db: AsyncSession):
    create_values = free_board_info.dict()
    create_values['Create_date'] = datetime.now(timezone.utc).replace(second=0, microsecond=0).replace(tzinfo=None)
    await db.execute(insert(Free_Board).values(create_values))

    
    
# Update
@rdb.dao(transactional=True)
async def update_free_board(free_board_no: int, free_board_info: UpdateBoard, db: AsyncSession) -> None:
    await db.execute(update(Free_Board).filter(Free_Board.Board_no == free_board_no).values(free_board_info.dict()))

    

# Delete
@rdb.dao(transactional=True)
async def delete_free_board(free_board_no: int, db: AsyncSession) -> None:
    await comm_free_dao.all_delete_free_board_comment(free_board_no, db)
    await db.execute(delete(Free_Board).filter(Free_Board.Board_no == free_board_no))


#sort
@rdb.dao()
async def sort_free_board(skip: int, sel: int, db: AsyncSession) -> tuple[int, list[ReadBoardlist]]:
    if sel == 0:
        result = await db.execute(select(Free_Board).order_by(Free_Board.Board_no.desc()).offset(skip*10).limit(10))
    elif sel == 1:
        result = await db.execute(select(Free_Board).order_by(Free_Board.Board_no.asc()).offset(skip*10).limit(10))
    elif sel == 2:
        result = await db.execute(select(Free_Board).order_by(Free_Board.Create_date.desc()).offset(skip*10).limit(10))
    elif sel == 3:
        result = await db.execute(select(Free_Board).order_by(Free_Board.Create_date.asc()).offset(skip*10).limit(10))
    elif sel == 4:
        result = await db.execute(select(Free_Board).order_by(Free_Board.Title.asc()).offset(skip*10).limit(10))
    elif sel == 5:
        result = await db.execute(select(Free_Board).order_by(Free_Board.Title.desc()).offset(skip*10).limit(10))
    elif sel == 6:
        result = await db.execute(select(Free_Board).order_by(Free_Board.Views.desc()).offset(skip*10).limit(10))
    elif sel == 7:
        result = await db.execute(select(Free_Board).order_by(Free_Board.Views.asc()).offset(skip*10).limit(10))
    free_board_info = result.scalars().all()
    total = await db.execute(select(func.count(Free_Board.Board_no)))
    total = total.scalar()
    return total, free_board_info


