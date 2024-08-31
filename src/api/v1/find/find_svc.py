from src.api.v1.find.find_dao import get_user_by_email_and_phone, get_user_by_phone
from typing import Optional

async def find_password(email: str, phone: str) -> Optional[str]:
    '''
    이메일과 전화번호를 사용하여 사용자의 비밀번호를 찾습니다.
    '''
    try:
        user = await get_user_by_email_and_phone(email, phone)

        # 사용자가 존재하고 비밀번호가 있는 경우 반환
        if user and user.user_password:
            return user.user_password
        else:
            return None
    except Exception as e:
        return None

async def find_email_(phone: str) -> Optional[str]:
    '''
    전화번호를 사용하여 사용자의 이메일을 찾습니다.
    '''
    try:
        email = await get_user_by_phone(phone)

        # 이메일이 있는 경우 반환
        if email:
            return email
        else:
            return None
    except Exception as e:
        return None