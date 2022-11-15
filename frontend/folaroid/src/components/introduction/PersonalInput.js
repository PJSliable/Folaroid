import React, { useEffect, useState } from 'react';
import {
    Button,
    Box,
    CardContent,
    TextField,
    InputBase,
    alpha,
    InputLabel,
} from '@mui/material';
import { AdapterDayjs } from '@mui/x-date-pickers/AdapterDayjs';
import { LocalizationProvider } from '@mui/x-date-pickers/LocalizationProvider';
import { DatePicker } from '@mui/x-date-pickers/DatePicker';
import dayjs from 'dayjs';
import { updatePersonal, getPersonal } from '../../modules/intro/personal';
import { useDispatch, useSelector, useStore } from 'react-redux';
import { useLocation } from 'react-router-dom';
import styled from '@emotion/styled';

const CardHeader = styled.div`
    border-radius: 10px 10px 0 0;
    background-color: rgba(140, 140, 140, 0.35);
    padding: 15px;
    font-size: 1.5rem;
    font-weight: bolder;
    color: white;
    display: flex;
    flex-direction: row;
    justify-content: space-between;
    align-items: center;
`;

const IntroTextField = styled(TextField)`
    .MuiOutlinedInput-root {
        color: white;
        fieldset {
            border-color: white;
        }
        &:hover fieldset {
            border-color: white;
        }
        .Mui-focused fieldset {
            border-color: white;
        }
    }
`;

const IntroInputLabel = styled(InputLabel)`
    color: white;
    margin-bottom: 5px;
`;

const IntroCardContent = styled(CardContent)`
    border-radius: 10px;
    background-color: rgba(44, 43, 43, 1);
    color: white;
    font-size: 1.1rem;
    padding: 20px 50px 20px 50px;
    border-radius: 0 0 10px 10px;
`;

const IntroBox = styled.div`
    width: 80%;
    margin: auto;
    margin-top: 10px;
    margin-bottom: 10px;
`;

function Update(props) {
    const person = props.person;
    const [personal, setPersonal] = useState(person);

    const handleInputChange = (event) => {
        const { name, value } = event.target;
        setPersonal({ ...personal, [name]: value });
    };
    const handleSubmit = (event) => {
        event.preventDefault();
        props.onCreate(personal);
    };

    return (
        <IntroCardContent>
            <Box>
                <form style={{ margin: '10px' }} onSubmit={handleSubmit}>
                    <div
                        style={{
                            width: '100%',
                            display: 'flex',
                            justifyContent: 'space-between',
                            flexDirection: 'column',
                        }}
                    >
                        <div style={{ width: '100%', margin: '20px' }}>
                            <IntroInputLabel shrink>이름</IntroInputLabel>
                            <IntroTextField
                                placeholder="이름"
                                name="userName"
                                onChange={handleInputChange}
                                value={personal.userName}
                            />
                        </div>
                        <div style={{ width: '100%', margin: '20px' }}>
                            <LocalizationProvider
                                dateAdapter={AdapterDayjs}
                                sx={{ width: '40%' }}
                            >
                                <IntroInputLabel shrink>
                                    생년월일
                                </IntroInputLabel>
                                <DatePicker
                                    inputFormat="YYYY년 MM월 DD일"
                                    value={personal.userBirth}
                                    name="userBirth"
                                    onChange={(newValue) => {
                                        setPersonal({
                                            ...personal,
                                            userBirth: dayjs(newValue)
                                                .toISOString()
                                                .substring(0, 10),
                                        });
                                    }}
                                    renderInput={(params) => (
                                        <IntroTextField
                                            id="date-input"
                                            {...params}
                                        />
                                    )}
                                />
                            </LocalizationProvider>
                        </div>
                        <div style={{ width: '100%', margin: '20px' }}>
                            <IntroInputLabel shrink>이메일</IntroInputLabel>
                            <IntroTextField
                                type="email"
                                placeholder="example@ssafy.com"
                                name="userEmail"
                                onChange={handleInputChange}
                                InputLabelProps={{
                                    shrink: true,
                                }}
                                style={{ width: '40%' }}
                                value={personal.userEmail}
                            />
                        </div>

                        <div style={{ width: '100%', margin: '20px' }}>
                            <IntroInputLabel shrink>연락처</IntroInputLabel>
                            <IntroTextField
                                label="연락처"
                                type="tel"
                                name="userPhone"
                                onChange={handleInputChange}
                                placeholder="010-0000-0000"
                                InputLabelProps={{
                                    shrink: true,
                                }}
                                style={{ width: '40%' }}
                                value={personal.userPhone}
                            />
                        </div>
                        <div
                            style={{
                                display: 'flex',
                                alignItems: 'center',
                                margin: '20px',
                                justifyContent: 'end',
                            }}
                        >
                            <Button type="submit" variant="outlined">
                                저장
                            </Button>
                        </div>
                    </div>
                </form>
            </Box>
        </IntroCardContent>
    );
}

function ReadName(props) {
    const userInfo = props.user;

    const handleClick = (e) => {
        props.onUpdate();
    };

    return (
        <IntroCardContent>
            <div style={{ display: 'flex', flexDirection: 'row' }}>
                <h3>이름</h3>
                <Box style={{ margin: '20px' }}>{userInfo.userName}</Box>
            </div>
            <div style={{ display: 'flex', flexDirection: 'row' }}>
                <h3>생년월일</h3>
                <Box style={{ margin: '20px' }}>{userInfo.userBirth}</Box>
            </div>
            <div style={{ display: 'flex', flexDirection: 'row' }}>
                <h3>이메일</h3>
                <Box style={{ margin: '20px' }}>{userInfo.userEmail}</Box>
            </div>
            <div style={{ display: 'flex', flexDirection: 'row' }}>
                <h3>전화번호</h3>
                <Box style={{ margin: '20px' }}>{userInfo.userPhone}</Box>
            </div>
            <div
                style={{
                    display: 'flex',
                    alignItems: 'center',
                    margin: '20px',
                    justifyContent: 'end',
                }}
            >
                <Button
                    onClick={handleClick}
                    variant="contained"
                    color="neutral"
                >
                    수정
                </Button>
            </div>
        </IntroCardContent>
    );
}

function ViewName() {
    const personal = useSelector((state) => state.personal);
    const { pathname } = useLocation();
    const store = useStore();
    const intro_no =
        pathname === '/intro'
            ? store.getState().auth.user.intro_no
            : store.getState().portfolio.pf.introNo;
    const [mode, setMode] = useState('READ');
    const dispatch = useDispatch();

    useEffect(() => {
        dispatch(getPersonal(intro_no));
    }, [dispatch, intro_no]);

    let content = null;
    if (mode === 'CREATE') {
        content = (
            <IntroBox>
                <CardHeader>개인정보</CardHeader>
                <Update
                    person={personal}
                    onCreate={(personal) => {
                        dispatch(
                            updatePersonal({
                                introNo: intro_no,
                                userBirth: personal.userBirth,
                                userEmail: personal.userEmail,
                                userName: personal.userName,
                                userPhone: personal.userPhone,
                            })
                        );
                        setMode('READ');
                    }}
                    personal={personal}
                ></Update>
            </IntroBox>
        );
    } else if (mode === 'READ') {
        content = (
            <IntroBox>
                <CardHeader>개인정보</CardHeader>
                <ReadName
                    user={personal}
                    onUpdate={() => {
                        setMode('CREATE');
                    }}
                ></ReadName>
            </IntroBox>
        );
    }

    return content;
}

export default ViewName;
