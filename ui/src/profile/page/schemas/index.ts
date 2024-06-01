import * as yup from 'yup';
import {Bundle} from "common/base/models/generic.model";
import {isBeforeNowString} from "common/base/utils/date";

const instagramRegex = /^(?:https?:\/\/)?(?:www\.)?instagram\.com\/([a-zA-Z\d\.\_\-]+)?/;
const twitterRegex = /^(?:https?:\/\/)?(?:www\.)?((x|twitter)\.com)\/([a-zA-Z\d\.\_\-]+)?/;
const nameRegex = /^[^\s\n\d_!¡?÷¿\/\\+=@#$%ˆ&*(){}|~<>;:[\]]{2,}$/;
const dobRegex = /^\d{4}-\d{2}-\d{2}$/;
// TODO: change this regex
const nicknameRegex = /^(?=.{5,15}$)[A-Za-z\d]+\w[A-Za-z\d]+/;

export const getProfileSchema = (bundle: Bundle, maxFileUploadSize: string) => {
    function isEmptyInput(input: string | undefined) {
        return input == undefined || input.length === 0;
    }

    function isValid(input: string | undefined, regex: RegExp) {
        return isEmptyInput(input) || regex.test(input!);
    }

    function isDateValid(input: string | undefined, regex: RegExp) {
        return isEmptyInput(input) || (regex.test(input!) && isBeforeNowString(input!));
    }

    function getMaxFileUploadSizeExceededMsg() {
        const msg = bundle['error.file.upload.size-exceeded'];
        return msg && msg.replace(/\{\d}/, maxFileUploadSize);
    }

    return yup.object().shape({
        instagramLink: yup.string().test(
            'valid-instagram-link',
            bundle['error.instagram.link'],
                link => isValid(link, instagramRegex)),
        xLink: yup.string().test(
            'valid-x-link',
            bundle['error.twitter.link'],
                link => isValid(link, twitterRegex)
        ),
        firstName: yup.string().test(
            'valid-firstname',
            bundle['error.name'],
                name => isValid(name, nameRegex)
        ),
        lastName: yup.string().test(
            'valid-lastname',
            bundle['error.name'],
                name => isValid(name, nameRegex)
        ),
        dob: yup.string().test(
            'valid-dob',
            bundle['error.dob'],
                dob => isDateValid(dob, dobRegex)
        ),
        bio: yup.string().notRequired(),
        nickname: yup.string().test(
            'valid-nickname',
            bundle['error.nickname'],
                name => isValid(name, nicknameRegex)
        ),
        profileImg: yup.mixed().test(
            'valid-file',
            getMaxFileUploadSizeExceededMsg(),
            file => {
                const f = file as File;
                return !f || f.size <= parseInt(maxFileUploadSize, 10) * 1000;
            }
        )
    });
}