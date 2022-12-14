import React, { useEffect, useState } from 'react';
import {
    Button,
    CardContent,
    TextField,
    Table,
    TableBody,
    TableCell,
    TableContainer,
    TableHead,
    TableRow,
    InputLabel,
} from '@mui/material';
import { DatePicker, LocalizationProvider } from '@mui/x-date-pickers';
import { AdapterDayjs } from '@mui/x-date-pickers/AdapterDayjs';
import dayjs from 'dayjs';
import {
    createSchool,
    getSchool,
    deleteSchool,
} from '../../modules/intro/school';
import { useDispatch, useSelector, useStore } from 'react-redux';
import { useLocation } from 'react-router-dom';
import styled from '@emotion/styled';
import { introSelector } from '../../modules/intro/introSelector';

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

const DeleteBtn = styled.button`
    border-radius: 50%;
    background-color: red;
    width: 18px;
    height: 18px;
    border: red;
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

const initialState = {
    schoolName: '',
    schoolDegree: '',
    schoolMajor: '',
    schoolAdmissionDate: null,
    schoolGraduationDate: null,
    schoolCredit: '',
    schoolMaxCredit: '',
};

export function SchoolInput() {
    const [school, setSchool] = useState(initialState);
    const { pathname } = useLocation();
    const store = useStore();
    const dispatch = useDispatch();

    const intro_no =
        pathname === '/intro'
            ? store.getState().auth.user.intro_no
            : store.getState().portfolio.pf.introNo;
    const [value, setValue] = useState({
        admission: null,
        graduation: null,
    });

    useEffect(() => {
        dispatch(getSchool(intro_no));
    }, [dispatch, intro_no]);

    const handleInputChange = (event) => {
        const { name, value } = event.target;
        setSchool({ ...school, [name]: value });
    };
    const onDeleteClick = () => {
        dispatch(introSelector.actions.outBoard('school'));
    };

    const handleSubmit = (event) => {
        event.preventDefault();
        const date = {
            admission: value.admission
                ? dayjs(value.admission)
                      .add(1, 'day')
                      .toISOString()
                      .substring(0, 10)
                : null,
            graduation: value.graduation
                ? dayjs(value.graduation)
                      .add(1, 'day')
                      .toISOString()
                      .substring(0, 10)
                : null,
        };
        dispatch(
            createSchool({
                introNo: intro_no,
                schoolName: school.schoolName,
                schoolDegree: school.schoolDegree,
                schoolMajor: school.schoolMajor,
                schoolAdmissionDate: date.admission,
                schoolGraduationDate: date.graduation,
                schoolCredit: school.schoolCredit,
                schoolMaxCredit: school.schoolMaxCredit,
            })
        );
        setSchool(initialState);
    };

    return (
        <IntroBox>
            <CardHeader>
                <div>??????</div>
                <DeleteBtn onClick={() => onDeleteClick()}></DeleteBtn>
            </CardHeader>

            <IntroCardContent>
                <form onSubmit={handleSubmit} style={{ margin: '10px' }}>
                    <div
                        style={{
                            width: '100%',
                            display: 'flex',
                            justifyContent: 'space-between',
                            flexDirection: 'column',
                        }}
                    >
                        <div style={{ width: '100%', margin: '20px' }}>
                            <IntroInputLabel>?????????</IntroInputLabel>
                            <IntroTextField
                                type="text"
                                placeholder="?????????"
                                InputLabelProps={{
                                    shrink: true,
                                }}
                                style={{ width: '40%' }}
                                name="schoolName"
                                onChange={handleInputChange}
                                value={school.schoolName}
                            />
                        </div>
                        <div style={{ display: 'flex', flexDirection: 'row' }}>
                            <div style={{ width: '100%', margin: '20px' }}>
                                <IntroInputLabel>??????</IntroInputLabel>
                                <IntroTextField
                                    placeholder="??????"
                                    size="medium"
                                    InputLabelProps={{
                                        shrink: true,
                                    }}
                                    style={{ width: '90%' }}
                                    name="schoolDegree"
                                    onChange={handleInputChange}
                                    value={school.schoolDegree}
                                />
                            </div>
                            <div style={{ width: '100%', margin: '20px' }}>
                                <IntroInputLabel>??????</IntroInputLabel>
                                <IntroTextField
                                    placeholder="??????"
                                    size="medium"
                                    InputLabelProps={{
                                        shrink: true,
                                    }}
                                    style={{ width: '90%' }}
                                    name="schoolMajor"
                                    onChange={handleInputChange}
                                    value={school.schoolMajor}
                                />
                            </div>
                        </div>
                        <div style={{ display: 'flex', flexDirection: 'row' }}>
                            <div style={{ width: '100%', margin: '20px' }}>
                                <IntroInputLabel>??????</IntroInputLabel>
                                <IntroTextField
                                    type="number"
                                    InputProps={{
                                        inputProps: {
                                            min: 0,
                                            max: 10,
                                            step: 0.1,
                                        },
                                    }}
                                    placeholder="??????"
                                    InputLabelProps={{
                                        shrink: true,
                                    }}
                                    style={{ width: '90%' }}
                                    name="schoolCredit"
                                    onChange={handleInputChange}
                                    value={school.schoolCredit}
                                />
                            </div>
                            <div style={{ width: '100%', margin: '20px' }}>
                                <IntroInputLabel>????????????</IntroInputLabel>
                                <IntroTextField
                                    type="number"
                                    InputProps={{
                                        inputProps: {
                                            min: 0,
                                            max: 10,
                                            step: 0.1,
                                        },
                                    }}
                                    placeholder="????????????"
                                    InputLabelProps={{
                                        shrink: true,
                                    }}
                                    style={{ width: '90%' }}
                                    name="schoolMaxCredit"
                                    onChange={handleInputChange}
                                    value={school.schoolMaxCredit}
                                />
                            </div>
                        </div>
                        <div style={{ display: 'flex', flexDirection: 'row' }}>
                            <div style={{ width: '100%', margin: '20px' }}>
                                <LocalizationProvider
                                    dateAdapter={AdapterDayjs}
                                >
                                    <IntroInputLabel>????????????</IntroInputLabel>
                                    <DatePicker
                                        views={['year', 'month']}
                                        inputFormat="YYYY??? MM???"
                                        value={value.admission}
                                        onChange={(newValue) => {
                                            setValue({
                                                ...value,
                                                admission: newValue,
                                            });
                                        }}
                                        renderInput={(params) => (
                                            <IntroTextField {...params} />
                                        )}
                                    />
                                </LocalizationProvider>
                            </div>
                            <div style={{ width: '100%', margin: '20px' }}>
                                <LocalizationProvider
                                    dateAdapter={AdapterDayjs}
                                    sx={{ width: '40%' }}
                                >
                                    <IntroInputLabel>????????????</IntroInputLabel>
                                    <DatePicker
                                        views={['year', 'month']}
                                        inputFormat="YYYY??? MM???"
                                        value={value.graduation}
                                        onChange={(newValue) => {
                                            setValue({
                                                ...value,
                                                graduation: newValue,
                                            });
                                        }}
                                        renderInput={(params) => (
                                            <IntroTextField {...params} />
                                        )}
                                    />
                                </LocalizationProvider>
                            </div>
                        </div>
                        <div
                            style={{
                                display: 'flex',
                                alignItems: 'center',
                                justifyContent: 'end',
                                margin: '20px',
                            }}
                        >
                            <Button
                                type="submit"
                                variant="contained"
                                color="neutral"
                                style={{ fontWeight: 'bolder' }}
                            >
                                ??????
                            </Button>
                        </div>
                    </div>
                </form>
            </IntroCardContent>
        </IntroBox>
    );
}

export function ReadSchool() {
    const dispatch = useDispatch();
    const { pathname } = useLocation();
    const store = useStore();
    const school = useSelector((state) => state.school);
    const [mode, setMode] = useState('OFF');
    const intro_no =
        pathname === '/intro'
            ? store.getState().auth.user.intro_no
            : store.getState().portfolio.pf.introNo;

    const onDeleteClick = (introSchoolNo) => {
        dispatch(deleteSchool(introSchoolNo));
    };

    if (school.length !== 0 && mode === 'OFF') {
        setMode('ON');
    } else if (Array.isArray(school) && school.length === 0 && mode === 'ON') {
        setMode('OFF');
    }

    let content = null;
    if (mode === 'ON') {
        content = (
            <IntroBox>
                <CardHeader>??????</CardHeader>
                <IntroCardContent>
                    <TableContainer>
                        <Table
                            style={{
                                backgroundColor: ' rgba(44, 43, 43, 1)',
                            }}
                        >
                            <TableHead>
                                <TableRow>
                                    <TableCell
                                        align="center"
                                        style={{ color: 'white' }}
                                    >
                                        ??????
                                    </TableCell>
                                    <TableCell
                                        align="center"
                                        style={{ color: 'white' }}
                                    >
                                        ??????
                                    </TableCell>
                                    <TableCell
                                        align="center"
                                        style={{ color: 'white' }}
                                    >
                                        ??????
                                    </TableCell>
                                    <TableCell
                                        align="center"
                                        style={{ color: 'white' }}
                                    >
                                        ??????
                                    </TableCell>
                                    <TableCell
                                        align="center"
                                        style={{ color: 'white' }}
                                    >
                                        ??????
                                    </TableCell>
                                    <TableCell
                                        align="center"
                                        style={{ color: 'white' }}
                                    >
                                        ??????
                                    </TableCell>
                                    <TableCell align="center"></TableCell>
                                </TableRow>
                            </TableHead>
                            <TableBody>
                                {school.map((item) => (
                                    <TableRow
                                        key={item.introSchoolNo}
                                        style={{ color: 'white' }}
                                    >
                                        <TableCell
                                            align="center"
                                            style={{ color: 'white' }}
                                        >
                                            {item.schoolName}
                                        </TableCell>
                                        <TableCell
                                            align="center"
                                            style={{ color: 'white' }}
                                        >
                                            {item.schoolMajor}
                                        </TableCell>
                                        <TableCell
                                            align="center"
                                            style={{ color: 'white' }}
                                        >
                                            {item.schoolDegree}
                                        </TableCell>
                                        <TableCell
                                            align="center"
                                            style={{ color: 'white' }}
                                        >
                                            {item.schoolAdmissionDate &&
                                                item.schoolAdmissionDate.substring(
                                                    0,
                                                    7
                                                )}
                                        </TableCell>
                                        <TableCell
                                            align="center"
                                            style={{ color: 'white' }}
                                        >
                                            {item.schoolGraduationDate &&
                                                item.schoolGraduationDate.substring(
                                                    0,
                                                    7
                                                )}
                                        </TableCell>
                                        <TableCell
                                            align="center"
                                            style={{ color: 'white' }}
                                        >
                                            {item.schoolCredit}/
                                            {item.schoolMaxCredit}
                                        </TableCell>
                                        <TableCell
                                            style={{
                                                display: 'flex',
                                                justifyContent: 'center',
                                            }}
                                            algin="center"
                                            onClick={() =>
                                                onDeleteClick(
                                                    item.introSchoolNo
                                                )
                                            }
                                        >
                                            <Button
                                                size="small"
                                                style={{
                                                    color: 'white'
                                                }}
                                                onClick={() =>
                                                    onDeleteClick(
                                                        item.introSchoolNo
                                                    )
                                                }
                                            >
                                                ??????
                                            </Button>
                                        </TableCell>
                                    </TableRow>
                                ))}
                            </TableBody>
                        </Table>
                    </TableContainer>
                </IntroCardContent>
            </IntroBox>
        );
    }
    return content;
}

export default SchoolInput;
