import React, { useEffect, useState } from 'react';
import { Button, Box, CardContent, TextField } from '@mui/material';
import { useDispatch, useSelector, useStore } from 'react-redux';
import { getImage, updateImage } from '../../modules/intro/image';
import { useLocation } from 'react-router-dom';
import styled from '@emotion/styled';

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

function ImageInput(props) {
    const [imageSrc, setImageSrc] = useState(null);

    const handleChange = (e) => {
        let formData = new FormData();
        const file = e.target.files[0];
        formData.append('file', file);
        setImageSrc(formData);
    };

    const handleSubmit = (event) => {
        event.preventDefault();
        props.onCreate(imageSrc);
    };

    return (
        <IntroCardContent>
            <Box>
                <form
                    onSubmit={handleSubmit}
                    style={{ margin: '10px' }}
                    encType="multipart/form-data"
                >
                    <div
                        style={{
                            width: '100%',
                            display: 'flex',
                            flexDirection: 'column',
                            justifyContent: 'space-between',
                        }}
                    >
                        <div style={{ width: '100%', margin: '20px' }}>
                            <IntroTextField
                                type="file"
                                accept="image/*"
                                style={{ width: '40%' }}
                                onChange={handleChange}
                                multiple
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
            </Box>
        </IntroCardContent>
    );
}

function ReadImage(props) {
    const handleClick = (e) => {
        props.onUpdate();
    };
    return (
        <IntroCardContent>
            <div>
                <img
                    style={{ width: '200px', height: '200px' }}
                    src={props.image}
                    alt="????????? ?????????"
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
                <Button
                    onClick={handleClick}
                    variant="contained"
                    color="neutral"
                >
                    ??????
                </Button>
            </div>
        </IntroCardContent>
    );
}

function ViewImage(props) {
    const image = useSelector((state) => state.image);
    const { pathname } = useLocation();
    const store = useStore();
    const intro_no =
        pathname === '/intro'
            ? store.getState().auth.user.intro_no
            : store.getState().portfolio.pf.introNo;
    const [mode, setMode] = useState('READ');
    const dispatch = useDispatch();

    useEffect(() => {
        dispatch(getImage(intro_no));
    }, [dispatch, intro_no]);

    let content = null;
    if (mode === 'CREATE') {
        content = (
            <IntroBox>
                <CardHeader>??????</CardHeader>
                <ImageInput
                    onCreate={(formData) => {
                        dispatch(updateImage({ intro_no, formData }));
                        setMode('READ');
                    }}
                ></ImageInput>
            </IntroBox>
        );
    } else if (mode === 'READ') {
        content = (
            <IntroBox>
                <CardHeader>??????</CardHeader>
                <ReadImage
                    image={image.imageLocation}
                    onUpdate={() => {
                        setMode('CREATE');
                    }}
                ></ReadImage>
            </IntroBox>
        );
    }

    return content;
}

export default ViewImage;
